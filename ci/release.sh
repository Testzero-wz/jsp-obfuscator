#!/bin/bash
set -e
profiles=("mac" "mac-aarch64" "win" "linux")

root_path=`pwd`
release_path=$root_path/release/
mkdir -p $release_path

for p in "${profiles[@]}"; do
  echo "Building profile: $p"

  # build
  mvn clean package -P$p

  # release
  cd dist/target/jsp-obfuscator-*-runtime/release && zip -r $release_path/jsp-obfuscator-$p.zip . && cd $root_path

  # plugin modules
  zip $release_path/plugin-sdk.zip api/target/api-*.jar plugin-common/target/plugin-common-*.jar

done
