#!/bin/bash

. ./setup.sh

gfsh <<!
connect --locator=localhost[10334];
stop server --name=server1;
stop server --name=server2;
stop locator --name=locator1;
exit;
!
