# ImageUpload

## Screenshot
![Screenshot_20240114-012419](https://github.com/lokesha348/ImageUpload/assets/30767349/3909fc9d-2c07-4c46-b515-a6cf7d4fa30d)
![Screenshot_20240114-012428](https://github.com/lokesha348/ImageUpload/assets/30767349/87ac0a42-bd95-4b47-b254-3ee21d7521cb)

## Image Upload App Documentation
## Overview
The Image Upload Component is designed to simplify the process of selecting and uploading images in Android applications. It provides a user-friendly interface for choosing images from the device's gallery or capturing pictures using the camera. Additionally, users can preview the selected image before initiating the upload process.

## Features
Image Source Options
The Image Upload Component supports two primary options for selecting an image:

1. Gallery: Users can choose an image from the device's gallery.
2. Camera: Users can capture a picture using the device's camera.

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
