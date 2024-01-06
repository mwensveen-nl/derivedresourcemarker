# Derived Resource Marker


This is an Eclipse plugin that can help to mark resources in a workspace as derived. 

**Derived resources are excluded in searches.**

You can use this plugin in two ways:

1. You can let the plugin determine which resources to mark as derived based on the preferences. 
1. You can select a resource (eg. workingset or project) and let the plugin mark all children of that resource as derived.

The plugin can use the pom packaging of a maven module to determine if the resources should be marked as derived.

## Installation
The plugin can be installed in Eclipse by using the update site.
Which can be found [here](http://mwensveen-nl.github.io/derivedresourcemarker/)
 

## Update version

** ATTENTION do not change other versions manually **

** all versions must be same BEFORE executiong set-version **

** e.g. in all pom.xml files: `2.3.0-SNAPSHOT` and all eclipse files `[MANIFEST.MF, feature.xml, category.xml]`: `2.3.0.qualifier` **

1. update the version in root `pom.xml`: e.g. `<newVersion>2.3.1-SNAPSHOT</newVersion>`
2. execute: `mvn clean tycho-versions:set-version` and all versions are updated now
