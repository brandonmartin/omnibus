#!/bin/bash

set -o errexit

echo "Starting omnibus build of $1"

export LEIN_ROOT=true
export CROSS_HOST=x86_64-w64-mingw32
export CROSS_BIN=/opt/mingw-w64/bin
#export PATH=/opt/opscode/embedded/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/gnu/bin:/usr/sfw/bin:/usr/gcc/4.3/bin:/var/ruby/1.8/gem_home/bin
export PATH=${CROSS_BIN}:/opt/opscode/embedded/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/gnu/bin:/usr/sfw/bin:/usr/gcc/4.3/bin:/var/ruby/1.8/gem_home/bin
#cd /root/omnibus
#git checkout master
#git pull >/tmp/omnibus.out 2>&1 
lein run --project-name "$1" --bucket-name "$2" --s3-access-key "$3" --s3-secret-key "$4" >>/tmp/omnibus.out 2>&1

echo "Finished build of $1"
