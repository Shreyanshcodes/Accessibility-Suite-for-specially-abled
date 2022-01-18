# Real Time Object Detection And Text To Speech using Flutter and Tensorflow
  This repository was created as a part of ``Accessibility Suite for Specially Abled``, a collection of apps designed to assist the specially abled in every day tasks more effectively.
  
# Table of Contents
  - [Overview](#real-time-object-detection-and-text-to-speech-using-flutter-and-tensorflow)
  - [Building and Installation](#building-and-installation)
  - [Technical Discussion](#technical-discussion)

## Problem Statement 
  Build an application using Flutter that can detect household objects via a live-video feed from the device's camera.
  
## Status
  I have completed the task accomplishing the following -
  - The App can detect household objects via a live-video feed from the device's camera with a **93%** accuracy.
  - A Rectangular/Square indicator is drawn on the screen indicating the position of the object
  - The App also speaks the name of the detected object 
  
## Preview GIFS and ScreenShots
  <div>
    <img src="/image6.jpg" alt="object detection" width="25%" height="25%"/>
  </div>
  
  # Building and Installation
  ## Prerequisites
  To build this app, you will need -
  - [Flutter SDK](https://flutter.dev)

  ## Cloning the Project Repo
  If you like using the terminal
   - Navigate to where you want to clone this repo
   - ```git clone https://github.com/aryang117/rt-Object-Detection.git```
  <br> </br>
  
  If you wish to clone using the browser only
   - Scroll up to the about section
   - To the left of about section, you will see a button with label ``Code``
   - Click on the code button and then on Download zip
   - Extract the zip and you have the project folder!
    
   [Other ways to get the project repo](https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository-from-github/cloning-a-repository)
  
  ## Build
  Now, to actually build the app
  - Clone this repo
  - Navigate to the folder where this repo has been cloned
  - ```flutter build apk --release``` to build a release build of the app
  
  ## Installation
  To install this on a real device, after building the app
  - Plug-in your phone via USB
  - In the project folder ``flutter install [device-id]`` (device-id -> device-id of your phone, run ``flutter devices`` to see your device's id)
  It should be installed now! Check it out! (Assuming no errors occured during build)
  
  
  # Technical Discussion
  ## Packages Used
  These are the different 3rd Party Packages I used to complete this project
  - camera (^0.8.1): To access the device's camera
  - tflite(^1.0.3): Tensorflow Lite
  - flutter_tts(^3.1.0): Text-To-Speech functionality 

  ## Platforms and APIs used
  I had to use some 3rd Party APIs and Platforms to implement some required and optional functionalities of the app.
   - [Tensorflow Lite](https://www.tensorflow.org/lite/) - to detect objects.
  
  ## Features Implemented
  The app already fulfills all the requirements given in the problem statement. 
  
  ### Extra Features Implemented
  I added a few features that were not part of the problem statement, but they were added to help in development and are just tiny QoL (Quality of Life) features to make it easier to perform all operations of the app.
  #### Object Location Indicator
  The App draws a rectangular/squared shape where the object is supposed to be.

  ### [Back to top](#real-time-object-detection-and-text-to-speech-using-flutter-and-tensorflow)
