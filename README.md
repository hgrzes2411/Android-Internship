# Virtual Wardrobe

A simple wardrobe management application that allows users to create and manage their own collection of items. Each item can be described and displayed with an image, while additional items can be imported from a mock API.

## Features

* Add new wardrobe items
* Provide a title, category, description, and image for each item
* Display locally added items in a dedicated list
* Browse items provided by a mock API
* Import selected API items into the local collection
* View local and imported items in separate sections

## How It Works

### Local Items

Users can add their own wardrobe items by providing:

* **Title**
* **Category**
* **Description**
* **Image**

Once added, the item is stored in the application's database and displayed in the local items list.

### API Items

The application also provides a separate section containing items retrieved from a mock API.

Users can browse these items and import selected ones into their own wardrobe. Imported items are saved to the application's database and become available alongside other local wardrobe items.

## Project Purpose

The project was created during my internship at **Grid Dynamics** as a practical application for learning and developing software development skills.

It demonstrates basic CRUD operations, database interaction, working with external data sources, and handling user-provided images.
