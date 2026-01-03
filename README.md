# ChatManager

![Latest Version](https://img.shields.io/modrinth/v/shadowedleaves-chatmanager?color=007bff&label=version)
![Total Downloads](https://img.shields.io/modrinth/dt/shadowedleaves-chatmanager?color=007bff&label=downloads)
![Open Issues](https://img.shields.io/github/issues/ShadowedLeaves/ChatManager?color=007bff)
![License: MIT](https://img.shields.io/badge/license-MIT-007bff)

**ChatManager** is an advanced, fully customizable chat management plugin.

This plugin is compatible with servers running on Spigot/Paper **1.21+**.

---

## Features

- Staff moderation tools
- Toggleable filters (chat, repetitive messages, URLs)
- Staff notification for filtered messages
- Lightweight and performant
- Configuration via commands and GUI
- Clear chat for online players

---

## Requirements

- Spigot/Paper **1.21+**
- Java **21+**
- ProtocolLib

---

## Installation

1. Download ChatManager from [Modrinth](https://modrinth.com/plugin/shadowedleaves-chatmanager)
2. Install [ProtocolLib](https://github.com/dmulloy2/ProtocolLib/releases/latest)
3. Place both `.jar` files into your `plugins/` folder
4. Restart your server

---

## Commands

| Command                        | Description                                               |
|--------------------------------|-----------------------------------------------------------|
| `/chatmanager` (`/cm`)         | Main ChatManager command                                  |
| `/cm clearchat`                | Clears the chat for all online players                    |
| `/cm config [setting] [value]` | Edit ChatManager configurations                           |
| `/cm gui`                      | Opens the ChatManager GUI                                 |
| `/cm help [commandName]`       | Provides help for ChatManager commands                    |
| `/cm logs [page]`              | Opens Chatmanager logs GUI with the specified page number |
| `/cm reload`                   | Reloads the ChatManager configuration files               |

---

## Permissions

| Permission                      | Description                                                     |
|---------------------------------|-----------------------------------------------------------------|
| `chatmanager.bypass`            | Allows a player to bypass all chat filters and restrictions     |
| `chatmanager.notify`            | Allows a player to receive notifications about flagged messages |
| `chatmanager.command.help`      | Allows a player to view help messages for ChatManager           |
| `chatmanager.command.config`    | Allows a player to modify ChatManager configuration             |
| `chatmanager.command.logs`      | Allows a player to access and modify chat logs                  |
| `chatmanager.command.reload`    | Allows a player to reload the ChatManager configuration files   |
| `chatmanager.command.clearchat` | Allows a player to clear the chat for all players               |

---

## License

Licensed under the **MIT License**.

---

## Contact

Developed by **ShadowedLeaves**

Discord: **shadowedleaves_**