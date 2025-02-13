![logo](https://github.com/user-attachments/assets/f6305af5-0f50-41f6-8c5a-28724e707da2)

# VehicleConnectSDK
[![Build](https://github.com/eclipse-ecsp/androidVehicleConnectSDK/actions/workflows/android.yml/badge.svg)](https://github.com/eclipse-ecsp/androidVehicleConnectSDK/actions/workflows/android.yml)
[![License Compliance](https://github.com/eclipse-ecsp/androidVehicleConnectSDK/actions/workflows/license-compliance.yml/badge.svg)](https://github.com/eclipse-ecsp/androidVehicleConnectSDK/actions/workflows/license-compliance.yml)

Android VehicleConnectSDK provide API interface for Remote Operation, User Authentication Using spring Auth, Vehicle Profile, Vehicle Association. So remote Operations mobile apps can be designed by using the VehicleConnectSDK.

# Table of Contents
* [Getting Started](#getting-started)
* [Usage](#usage)
* [Built with Dependencies](#built-with-dependencies)
* [How to contribute](#how-to-contribute)
* [Code of Conduct](#code-of-conduct)
* [Contributors](#contributors)
* [Security Contact Information](#security-contact-information)
* [Support](#support)
* [Troubleshooting](#troubleshooting)
* [License](#license)
* [Announcements](#announcements)


## Getting Started

Take clone of the project by using the below command and repository path ans setup the xcode version 15.4 amd above (git clone https://github.com/eclipse-ecsp/androidVehicleConnectSDK.git)

Design Overview of VehicleConnectSDK
<img width="1162" alt="350999035-8682f027-d5da-4753-b130-b327d2277f81" src="https://github.com/user-attachments/assets/9fa8e708-0d17-44db-b0e4-dadc152ebb62">


Developer documentation:[Developer Doc.zip](https://github.com/user-attachments/files/16346021/Developer.Doc.zip)



### Prerequisites

SDK has been written in Kotlin and compatible with Android 7.0 (API level 24) and above android devices.
VehicleConnectSDK has capability to make a connection to cloud and get the response format model object, and raw data.

### Installation

1. Checkout the project from github and open the project using Android Studio and run the gradle sync and do rebuild.
2. After build successful, Find the [androidVehicleConnectSDK-debug.aar] in build/outputs/aar folder and add into your application project lib folder.
3. Implement the SDK/aar file (which is added into the lib folder of your application project) by using implementation statement inside build.gradle.kts (app level).
eg: implementation(files("libs/androidVehicleConnectSDK.aar")).
4. Use the AppManager instance (A class inside the VehicleConnectSDK) to initialize the sdk.
5. Download the sample app https://github.com/eclipse-ecsp/androidVehicleConnectApp for reference to know how we call API methods.

### Coding style check configuration

Check the Coding Guideline document: [Android Coding Guidelines.pdf](https://github.com/user-attachments/files/16709676/Android.Coding.Guidelines.pdf)

Use android lint for code warnings and errors

### Running the tests

Run the test classes under the folder "app/src/test/java/com/harman/sdkapp"

### Deployment

Find the [androidVehicleConnectSDK-debug.aar] in build/outputs/aar folder and add into your application project lib folder.
Implement the SDK/aar file (which is added into the lib folder of your application project) by using implementation statement inside build.gradle.kts (app level).
eg: implementation(files("libs/androidVehicleConnectSDK.aar"))

## Usage

Developer documentation: [Developer Doc.zip](https://github.com/user-attachments/files/16346025/Developer.Doc.zip)


## Built With Dependencies

* [AppAuth] - Auth library for User Authentication
* [Retrofit]- For network operation Management
* [Android Lint tool] - Coding convention and style guide


## How to contribute

Please read [CONTRIBUTING.md](https://github.com/eclipse-ecsp/androidVehicleConnectSDK/blob/main/CONTRIBUTING.md) for details on our contribution guidelines, and the process for submitting pull requests to us.

## Code of Conduct

Please read  [CODE_OF_CONDUCT.md](https://github.com/eclipse-ecsp/androidVehicleConnectSDK/blob/main/CODE_OF_CONDUCT.md) for details on our code of conduct, and the process for submitting pull requests to us.


## Contributors

Check here the list of [contributors](https://github.com/eclipse-ecsp/androidVehicleConnectSDK/graphs/contributors) who participated in this project.


## Security Contact Information

Please read [SECURITY.md](./SECURITY.md) to raise any security related issues.

## Support
Please write to us at [csp@harman.com](mailto:csp@harman.com)

## Troubleshooting

Please read [CONTRIBUTING.md](./CONTRIBUTING.md) for details on how to raise an issue and submit a pull request to us.

## License

This project is licensed under the Apache-2.0 License - see the [LICENSE](./LICENSE) file for details.

## Announcements

All updates to this library are present in our [releases page](https://github.com/eclipse-ecsp/androidVehicleConnectSDK/releases).
For the versions available, see the [tags on this repository](https://github.com/eclipse-ecsp/androidVehicleConnectSDK/tags).
