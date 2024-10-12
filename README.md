# Domain Deprecator Paper Plugin

A Plugin for [Paper](https://papermc.io) Minecraft Servers that detects the Domain used by a Player and sends them Messages depending on which one they use.

## What is this (originally) for?

When moving your Minecraft Server from one Domain to another you will need to notify your players.<br>

This plugin might help you in the early stages of a move where you want to gracefully tell your players to update their Server Lists.

## Features

The Plugin supports per-Domain MOTDs and Join Messages.

All these aspects of the plugin can be customized in the config file and live-reloaded using the `/domain-deprecator` command.
The Command requires the `domain-deprecator.admin` Permission.

The default config can be seen [here](./src/main/resources/config.yml).
