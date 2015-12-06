#!/bin/bash

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
      git clone --quiet --branch=gh-pages https://peterkir:${GH_TOKEN}@github.com/peterkir/org.eclipse.oomph.git . || error_exit "Error cloning gh-pages"
    
      export BINTRAY_URL=https://dl.bintray.com/peterkir/generic/$BINTRAY_PACKAGE/$VERSION/$TRAVIS_BRANCH/$TRAVIS_BUILD_NUMBER
      echo -e "updating index.html with build details - $BINTRAY_URL"
      cp index.html_template index.html
      INDEX_HTML=index.html
      sed -i -e 's|$DATE|'$DATE'|g'                               $INDEX_HTML
      sed -i -e 's|$BINTRAY_PACKAGE|'$BINTRAY_PACKAGE'|g'         $INDEX_HTML
      sed -i -e 's|$BINTRAY_URL|'$BINTRAY_URL'|g'                 $INDEX_HTML
      sed -i -e 's|$VERSION|'$VERSION'|g'                         $INDEX_HTML
      sed -i -e 's|$TRAVIS_BRANCH|'$TRAVIS_BRANCH'|g'             $INDEX_HTML
      sed -i -e 's|$TRAVIS_JOB_NUMBER|'$TRAVIS_JOB_NUMBER'|g'     $INDEX_HTML
      sed -i -e 's|$TRAVIS_BUILD_ID|'$TRAVIS_BUILD_ID'|g'         $INDEX_HTML
      sed -i -e 's|$TRAVIS_BUILD_NUMBER|'$TRAVIS_BUILD_NUMBER'|g' $INDEX_HTML
      sed -i -e 's|$TRAVIS_COMMIT|'$TRAVIS_COMMIT'|g'             $INDEX_HTML
      cat $INDEX_HTML
	
	  echo -e "storing for branch $TRAVIS_BRANCH latest build number $TRAVIS_BUILD_NUMBER
	  mkdir $TRAVIS_BRANCH
	  echo $TRAVIS_BUILD_NUMBER> $TRAVIS_BRANCH/latest
	  
      # add, commit and push files
      git add -f .
      git commit -m "[ci skip] Deploy Travis build #$TRAVIS_BUILD_NUMBER for branch $TRAVIS_BRANCH to gh-pages"
      git push -fq origin gh-pages || error_exit "Error uploading the build result to gh-pages"
    
      echo -e "Done with deployment to gh-pages\n"

  fi
fi
