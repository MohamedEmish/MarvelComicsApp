# Marvel Comics App
Mohamed Emish
## Features

- Retrieve a list of Marvel Comics from the [marvel api](https://developer.marvel.com/) and show them in a grid list
- See more details about a selected on a new screen.

## Tech

- Kotlin
- Room
- Retrofit
- Modularization
- Constraint layout
- MVVM
- Coroutines
- Mock
- Hilt

## Architecture

The architecture used in this project is MVVM.
We will instantiate the use cases in the VM, where the UI will be listening flow in case a flow value is changed, 
each use case will provide us a result from a single feature. 
The repositories will be handling the source where the data is provided (although in this project we won't be switching from local to remote in any feature). 
The data source will access the framework to retrieve the response.


## Modularization

The modules are separated by layers, respecting SOLID:

- App Module houses the injected components and the Application class

- Base Module houses base classes to be extended when needed

- Common Module houses shared constants, extensions, and generic functions

- Data Module which is responsible for providing a single source of data. It implements the repository interface defined in the domain layer.

- Domain Module which contains the use-case responsible for enclosing a particular task, repository interfaces, and entities.

- Feature Module houses the main activity as the main feature, we can create a module for each feature the same way

- Local Module handles local DB functions and logic

- Remote Module handles remote API and data handling



