# Example BFF
 This project is an example of using PCA BFF starter

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

```
JDK 1.8
Intellij IDE Community
```

### Dev environment Set up

To get a development env running this steps should be performed:

#### 1) Checkout the source code from tfs repository

```
git clone https://<url>.git
```

#### 2) Configure the Intellij IDE
In order to configure the intellij IDE follow this steps:

- Install the plugins listed bellow:

```
Lombok
FindBugs-IDEA
Save Actions
Gauge
Markdown support
PlantUML integration
```

- Configure the code style: 

From the menu **File -> Settings -> Editor -> Coding style- > java**  
a) click on the setting dropdown list next to schema

b) chose Import scheme -> Intellij IDEA Coding Style XML

c) Import the file PCA_Code_Style.xml

- Configure Lombok

Enable the annotation processors from:
**File -> Settings -> Settings -> Build, Execution, Deployment ->  Compiler -> Annotation Processors**

- Configure Save actions

The save actions can be configured from **File -> Settings -> Other settings**

Check the combo boxes:
```
Activate save actions on save
Optimize imports
Reformat file
Rearange fields and methods
Compile files
Reload files in running debugger
Add final modifier to local variable or parameter
Add missing @Override annotations
Add blocks to if/while/for statements
Add a serialVersionUID field for Serializable classes
Remove unecessary this to field and methods
```
- Configure the copyright

First create a scope that include java files from the menu **File -> Appearance and Behavior -> Scopes** 

````
file:*.java
````

then Go to **File -> Settings -> Editor -> Copyright -> Copyright Profiles.** and create a Copyright profile like :

````
Copyright (c) $today.year. by PCA, All rights reserved.
This source code, and resulting software, is the confidential and proprietary information
("Proprietary Information") and is the intellectual property ("Intellectual Property")
 of PCA ("The Company"). You shall not disclose such Proprietary Information and
shall use it only in accordance with the terms and conditions of any and all license
agreements you have entered into with The Company.
````

From the menu **File -> Settings -> Editor -> Copyright** select the created profile as the project default copyright for the defined scope. 


#### 3) Branching strategy:
The following branches should be maintained: <br>
 - master : a branch with the last high quality code to be delivered to test
 - develop: this branch serves as an integration branch for features. 
 - features branches: for new functionalities
 - bugfix branches for bug fixes 
 - hotfix branches for bugs detected in production, this branches should be pulled from the release tag

The naming to be used is:
```
feature/feature-name 
bugfix/fix_discription
hotfix/fix_description
```

#### 4) Running the application 

- From Intellij IDE:<br>
Create a gradle run configuration **Run -> Edit Configurations** with:<br>
task : bootRun <br>
Environment variables: `CONFIG=dev`

#### 5) Running the test 

- From Intellij IDE:<br>
Create a gradle run configuration **Run -> Edit Configurations** with:<br>
task : test <br>
Environment variables: `CONFIG=junit`