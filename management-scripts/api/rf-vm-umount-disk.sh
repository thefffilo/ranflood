#!/bin/sh

set -eu

if test $# -lt 1; then
	echo "Usage: $0 <host> <mountpoint>" 2>&1
	exit 1
fi

SCRIPTDIR="$( cd $(dirname $0); pwd )"

TARGET="$1"
ssh "$TARGET" umount "$2"
