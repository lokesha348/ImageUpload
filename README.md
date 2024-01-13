# ImageUpload

## Screens
![Screenshot_20240114-012445](https://github.com/lokesha348/ImageUpload/assets/30767349/c15d640a-06da-40cd-98bb-b3d2af4a958b)
![Screenshot_20240114-012441](https://github.com/lokesha348/ImageUpload/assets/30767349/376ced1e-5f1d-403c-a061-747413f563b4)
![Screenshot_20240114-012428](https://github.com/lokesha348/ImageUpload/assets/30767349/fe67450c-06a6-448d-a773-9c1a630b560d)
![Screenshot_20240114-012419](https://github.com/lokesha348/ImageUpload/assets/30767349/aeb016dc-65eb-4a2b-8afe-0785b64b9eb2)

## Image Upload App Documentation
## Overview
The Image Upload Component is designed to simplify the process of selecting and uploading images in Android applications. It provides a user-friendly interface for choosing images from the device's gallery or capturing pictures using the camera. Additionally, users can preview the selected image before initiating the upload process.

## Features
Image Source Options
The Image Upload Component supports two primary options for selecting an image:

1.Gallery: Users can choose an image from the device's gallery.
2.Camera: Users can capture a picture using the device's camera.

## Preview Functionality
Once an image is selected, users can preview it by tapping the "Preview" button. This feature allows users to confirm their selection before proceeding with the upload.

## Permissions Handling
The component takes care of runtime permissions required for accessing the device's storage and camera.

## Network State Check
Before initiating the image upload process, the component performs a network state check. This ensures that the device has a stable internet connection, preventing potential issues during the upload.

## Simulated Image Upload

In the absence of a dedicated test API for image upload, the Image Upload Component simulates the upload process using a delayed handler. This is a temporary solution to replicate the user experience during an image upload.

### Usage Note

- **Loader Display:** Upon tapping the "Submit" button, a loading indicator is displayed to simulate the image upload process. This is achieved using `postDelayed` to introduce a delay similar to what might occur during a real API call.
