#!/bin/bash

cd BaToImperator
mvn package
cp target/BaToImperator.jar ../Release/BaToImperator
cd ..