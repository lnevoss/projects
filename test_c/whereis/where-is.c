#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_DEPTH 4
#define MAX_THREADS 64

/*
    TODO:
        indexing
        reindexing
*/


typedef struct {
    char dirpath[MAX_PATH];
    const char *target;
    int depth;
} ThreadData;

DWORD WINAPI search_dir_thread(LPVOID param);

char* stristr(const char* haystack, const char* needle) {
  do {
    const char* h = haystack;
    const char* n = needle;
    while (tolower((unsigned char) *h) == tolower((unsigned char ) *n) && *n) {
      h++;
      n++;
    }
    if (*n == 0) {
      return (char *) haystack;
    }
  } while (*haystack++);
  return 0;
}

void search_dir(const char *dirpath, const char *target, int depth) {
    char search_path[MAX_PATH];
    snprintf(search_path, sizeof(search_path), "%s\\*", dirpath);

    WIN32_FIND_DATAA ffd;
    HANDLE hFind = FindFirstFileA(search_path, &ffd);
    if (hFind == INVALID_HANDLE_VALUE) {
        return;
    }

    HANDLE threadHandles[MAX_THREADS];
    int threadCount = 0;

    do {
        if (strcmp(ffd.cFileName, ".") == 0 || strcmp(ffd.cFileName, "..") == 0)
            continue;

        char fullpath[MAX_PATH];
        snprintf(fullpath, sizeof(fullpath), "%s\\%s", dirpath, ffd.cFileName);

        if (ffd.dwFileAttributes & FILE_ATTRIBUTE_REPARSE_POINT)
            continue;

        if (ffd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) {
            if (depth < MAX_DEPTH && threadCount < MAX_THREADS) {
                ThreadData *data = malloc(sizeof(ThreadData));
                if (!data) {
                    continue;
                }
                strncpy(data->dirpath, fullpath, MAX_PATH);
                data->target = target;
                data->depth = depth + 1;

                HANDLE hThread = CreateThread(
                    NULL, 0,
                    search_dir_thread,
                    data,
                    0,
                    NULL);

                if (hThread) {
                    threadHandles[threadCount++] = hThread;
                } else {
                    free(data);
                    search_dir(fullpath, target, depth + 1);
                }
            } else {
                search_dir(fullpath, target, depth + 1);
            }
        } else {
            if (stristr(ffd.cFileName, target) != NULL) {
                printf("%s\n", fullpath);
            }
        }
    } while (FindNextFileA(hFind, &ffd) != 0);

    FindClose(hFind);

    if (threadCount > 0) {
        WaitForMultipleObjects(threadCount, threadHandles, TRUE, INFINITE);
        for (int i = 0; i < threadCount; i++) {
            CloseHandle(threadHandles[i]);
        }
    }
}

DWORD WINAPI search_dir_thread(LPVOID param) {
    ThreadData *data = (ThreadData*)param;
    search_dir(data->dirpath, data->target, data->depth);
    free(data);
    return 0;
}

int main(int argc, char *argv[]) {
    if (argc < 2) {
        printf("%s <target>\n", argv[0]);
        return 1;
    }

    char drives[256];
    DWORD len = GetLogicalDriveStringsA(sizeof(drives), drives);
    if (len == 0) {
        return 1;
    }

    const char *target = argv[1];
    char *drive = drives;

    while (*drive) {
        search_dir(drive, target, 0);
        drive += strlen(drive) + 1;
    }

    return 0;
}
