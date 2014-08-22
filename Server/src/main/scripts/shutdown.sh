. ./setup.sh

gfsh <<!
connect --locator=localhost[10334];
shutdown --time-out=60
Y
!

