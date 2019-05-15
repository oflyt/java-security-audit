#!/bin/sh
set -x
body='{"name":"melon","description":"Real melon from Italy", "data":["java.util.HashMap",{"cost":2,"color":"yellow"}]}'
auth="Authorization: $(printf 'iamvalid' | base64 -w 0)"
curl -i -X POST --header "$auth" -d "$body" http://localhost:8888/products; echo
