import argparse
import json
import os
import subprocess
import sys

from openai import OpenAI

API_KEY = os.getenv("OPENROUTER_API_KEY")
BASE_URL = os.getenv("OPENROUTER_BASE_URL", default="https://openrouter.ai/api/v1")


def main():
    p = argparse.ArgumentParser()
    p.add_argument("-p", required=True)
    args = p.parse_args()

    tools = [
        {
            "type":"function",
            "function":{
                "name":"Read",
                "description":"Read and return the contents of a file",
                "parameters":{
                    "type":"object",
                    "properties":{
                        "file_path":{
                            "type":"string",
                            "description":"The path to the file to read"
                        }
                    }
                },
                "required":["file_path"]            
            }
        },
        {
            "type": "function",
            "function": {
                "name": "Write",
                "description": "Write content to a file",
                "parameters": {
                "type": "object",
                "required": ["file_path", "content"],
                "properties": {
                    "file_path": {
                        "type": "string",
                        "description": "The path of the file to write to"
                    },
                    "content": {
                        "type": "string",
                        "description": "The content to write to the file"
                    }
                }
                }
            }
        },
        {
            "type": "function",
            "function": {
                "name": "Bash",
                "description": "Execute a shell command",
                "parameters": {
                    "type": "object",
                    "required": ["command"],
                    "properties": {
                        "command": {
                            "type": "string",
                            "description": "The command to execute"
                        }
                    }
                }
            }
        },
    ]

    messages=[{"role": "user", "content": args.p}]
    response = call_llm(messages, tools)
    

    while response.tool_calls:
        messages.append(response)
        for tool_call in response.tool_calls:
            messages.append(execute_tool_call(tool_call))
        response = call_llm(messages, tools)

    print(response.content)


def call_llm(messages: list, tools: list):
    if not API_KEY:
        raise RuntimeError("OPENROUTER_API_KEY is not set")

    client = OpenAI(api_key=API_KEY, base_url=BASE_URL)
    chat = client.chat.completions.create(
        model="anthropic/claude-haiku-4.5",
        messages=messages,
        tools=tools,
    )

    if not chat.choices or len(chat.choices) == 0:
        raise RuntimeError("No choices in response")
    
    return chat.choices[0].message
    

def execute_tool_call(tool_call: dict):
    tool_call_id = tool_call.id
    name = tool_call.function.name
    arguments = json.loads(tool_call.function.arguments)
    file_path = arguments.get("file_path")
    

    match name:
        case "Read":
            with open(file_path) as f:
                return {
                    "role": "tool",
                    "tool_call_id": tool_call_id,
                    "content": f.read(),
                }
        
        case "Write":
            content = arguments["content"]
            with open(file_path, "w") as f:
                f.write(content)
                return {
                    "role": "tool",
                    "tool_call_id": tool_call_id,
                    "content": f'Successfully wrote to {file_path}',
                }
                
        case "Bash":
            command = arguments["command"]
            try:
                ans = subprocess.check_output(command.split(), text=True)
                return {
                    "role": "tool",
                    "tool_call_id": tool_call_id,
                    "content": ans,
                }

            except subprocess.CalledProcessError as e:
                return {
                    "role": "tool",
                    "tool_call_id": tool_call_id,
                    "content": e,
                }
        
        case _: 
            return {
                    "role": "tool",
                    "tool_call_id": tool_call_id,
                    "content": "Called tool is missing",
                }


if __name__ == "__main__":
    main()