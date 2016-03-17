#!/bin/bash

printenv|sort

function error_exit
{
  echo -e "\e[01;31m$1\e[00m" 1>&2
  exit 1
}

if [ "$TRAVIS_PULL_REQUEST" == "false" ] 
then

   if [[ "$TRAVIS_BRANCH" == "master" || "$TRAVIS_BRANCH" == "peterkir" ]]
   then
		echo -e "Starting to deploy to gh-pages - updating $TRAVIS_BRANCH\n"
		
		# create and cd into temporary deployment work directory
		mkdir deployment-work
		cd deployment-work
		
		# setup git and clone from gh-pages branch
		git config --global user.email "travis-deployer@klib.io"
		git config --global user.name "Travis Deployer"
		git clone --quiet --branch=gh-pages https://peterkir:${GH_TOKEN}@github.com/peterkir/org.eclipse.oomph.git . > /dev/null 2>&1 || error_exit "Error cloning gh-pages"
		
		export BINTRAY_ROOT_URL=https://dl.bintray.com/peterkir/generic

		echo -e "updating builds.json with build details"

		CurrentBuildJson='{builds: [\n'
		CurrentBuildJson+='   {\n'
		CurrentBuildJson+='      TRAVIS_BUILD_ID: "'$TRAVIS_BUILD_ID'"\n'
		CurrentBuildJson+='      TRAVIS_BUILD_NUMBER: "'$TRAVIS_BUILD_NUMBER'"\n'
		CurrentBuildJson+='      TRAVIS_JOB_NUMBER: "'$TRAVIS_JOB_NUMBER'"\n'
		CurrentBuildJson+='      DATE: "'$DATE'"\n'
		CurrentBuildJson+='      BINTRAY_ROOT_URL: "'$BINTRAY_ROOT_URL'"\n'
		CurrentBuildJson+='      BINTRAY_PACKAGE: "'$BINTRAY_PACKAGE'"\n'
		CurrentBuildJson+='      VERSION: "'$VERSION'"\n'
		CurrentBuildJson+='      BRANCH: "'$TRAVIS_BRANCH'"\n'
		CurrentBuildJson+='      COMMIT: "'$TRAVIS_COMMIT'"\n'
		CurrentBuildJson+='   },'
		
		echo -e "CurrentBuildJson=${CurrentBuildJson}"
		
		sed -i -e 's|{builds: \[|'${CurrentBuildJson}'|g' builds.json
		echo -e "new build JSON looks like this"
		cat builds.json
			
		echo -e "storing for branch $TRAVIS_BRANCH latest build number $TRAVIS_BUILD_NUMBER"
	
		mkdir $TRAVIS_BRANCH > /dev/null 2>&1
		echo $TRAVIS_BUILD_NUMBER> $TRAVIS_BRANCH/latest
	
		# add, commit and push files
		git add -f .
		git commit -m "[ci skip] Deploy Travis build #$TRAVIS_BUILD_NUMBER for branch $TRAVIS_BRANCH to gh-pages"
		git push -fq origin gh-pages > /dev/null 2>&1 || error_exit "Error uploading the build result to gh-pages"
		
		echo -e "Done with deployment to gh-pages\n"

  fi
fi
