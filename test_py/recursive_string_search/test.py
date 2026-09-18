import os

target = 'GetDifficultyFromSettings'
class_name = 'XComGameState_CampaignSettings'
destination = 'E:\\SteamLibrary\\steamapps\\workshop\\content\\268500'

def recursive_search(dirpath):
    curdir = os.listdir(dirpath)
    for file in curdir:
        fullpath = os.path.join(dirpath, file)

        if os.path.isfile(fullpath):
            if file.endswith(('.ini', '.uc', '.upk', '.txt')):
                try:
                    with open(fullpath, 'r', encoding='utf-8', errors='ignore') as f:
                        contents = f.read()
                        if target in contents:
                            print(f"Found '{target}' in {fullpath}")
                except Exception as e:
                    print(f"Error reading {fullpath}: {e}")

        elif os.path.isdir(fullpath):
            recursive_search(fullpath)

def recursive_binary_search(dirpath):
    for root, _, files in os.walk(dirpath):
        for file in files:
            path = os.path.join(root, file)
            try:
                with open(path, 'rb') as f:
                    data = f.read()
                    if target.encode() in data:
                        print(f"[BINARY HIT] {target} found in: {path}")
            except Exception as e:
                print(f"Error reading {path}: {e}")

def search_for_class_and_function(search_path):
    for root, _, files in os.walk(search_path):
        for file in files:
            if file.endswith(('.uc', '.txt', '.ini')):
                path = os.path.join(root, file)
                try:
                    with open(path, 'r', encoding='utf-8', errors='ignore') as f:
                        content = f.read()
                        if class_name in content and target in content:
                            print(f"[HIT] Both {class_name} and {target} found in: {path}")
                except Exception as e:
                    print(f"Error reading {path}: {e}")

recursive_search(destination)
recursive_binary_search(destination)
search_for_class_and_function(destination)
