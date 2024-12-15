#!/bin/bash

# 检查输入参数是否正确
if [ $# -ne 2 ]; then
  echo "Usage: $0 <verilog_file> <path_prefix>"
  exit 1
fi

verilog_file=$1
path_prefix=$2

# 检查文件是否存在
if [ ! -f "$verilog_file" ]; then
  echo "Error: File $verilog_file does not exist."
  exit 1
fi

# 删除路径中的 path_prefix
sed -i "s|@\[$path_prefix/|@[|g" "$verilog_file"

# 去掉 io_ 
# sed -i 's/\bio_\([a-zA-Z0-9_]*\)/\1/g' "$verilog_file"

echo "Processed file: $verilog_file."
