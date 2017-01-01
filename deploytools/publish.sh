#!/usr/bin/env bash

# If you want to build the project and update your Lambda function quickly, use this!
# Replace the S3 bucket name with your own. I don't recommend uploading this straight
# to Lambda through the AWS CLI, it'll go poorly if it goes at all 'cause the .jar's
# too big.

pushd ..
echo "This could take more than a few minutes..."
echo "The following is the current branch that we'll be pushing."
git branch
sbt compile && sbt assembly
aws s3 cp --region us-east-1 target/scala-2.11/DratiniDex-assembly-1.0.jar s3://com.clydemachine.dratinidex/DratiniDex.jar --debug
clear
aws lambda update-function-code --region us-east-1 --function-name "DratiniDex" --s3-bucket "com.clydemachine.dratinidex" --s3-key "DratiniDex.jar" --debug
popd
echo "----"
echo "Done! Test the skill now.\nRemember that updates to IntentSchema, POKENAMES list, or the SampleUtterances MUST BE DONE MANUALLY."
read -n1 -r -p "Press any key to continue..." key