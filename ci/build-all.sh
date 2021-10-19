#!/usr/bin/env -S bash -e

MVN_PROJECTS=$( find . -name pom.xml -print -or -path '*/target/*' -prune | sort | xargs -n 1 dirname )

# first run to download all the Maven dependencies without logging
for project in $MVN_PROJECTS
do
    pushd "$project"
    mvn -B -q clean package -DskipTests=true
    popd
done

for project in $MVN_PROJECTS
do
    pushd "$project"
    mvn -B clean verify
    popd
done
