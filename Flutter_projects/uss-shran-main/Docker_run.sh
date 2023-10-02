#!/bin/bash
echo "Which world do you want to enter? apiserver or server: "
read server_var

echo "Please input world size: "
# shellcheck disable=SC2162
read world_var
echo "Please input the amount of obs: "
# shellcheck disable=SC2162
read obs_var

cd /srv

java -jar "${server_var}".jar -s "${world_var}" -o "${obs_var}"

