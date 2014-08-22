#!/bin/bash
for i in `seq 1 1000`;
do
  echo "put --region=demoRegion --key=\"$i\" --value=\"Value $i\""
done  

