#!/bin/bash

gfsh <<EOF
connect --locator=localhost[10334];
list members;
list regions;
query --query="select * from /tradeRegion limit 5";
exit;
EOF

