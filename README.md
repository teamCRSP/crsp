ViGEm Bus Driver
Windows kernel-mode driver emulating well-known USB game controllers.

Build status GitHub All Releases Discord Website GitHub followers Mastodon Follow

🧟 THIS PROJECT HAS BEEN RETIRED 🧟
Users of this software are encouraged to read the end-of-life statement. So long, cheers 🖖

About
The ViGEmBus driver and ViGEmClient libraries represent the core of the Virtual Gamepad Emulation Framework (or ViGEm , for short). ViGEm aims for a 100% accurate emulation of well-known gaming peripherals as pure software-based devices at kernel level. As it mimics "the real thing" games and other processes require no additional modification whatsoever to detect ViGEm-based devices (no Proxy-DLLs or API-Hooking) and simply work out of the box. While the (now obsolete) Scarlett.Crush Productions Virtual Bus Driver is the spiritual father of this project, ViGEm has been designed and written from the ground up utilizing Microsoft's Kernel-Mode Driver Framework.

Emulated devices
Emulation of the following USB Gamepads is supported:

Microsoft Xbox 360 Controller
Sony DualShock 4 Controller
Use cases
A few examples of the most common use cases for ViGEm are:

You have an unsupported input device you'd like to use within games without modifying said game.
You want the freedom to use a different controller of your choice in PS4 Remote Play.
You encountered a game not compatible with x360ce (prior to version 4.x).
You want to extend the reach of your input device (like send traffic to a different machine over a network).
You want to test/benchmark your game and need a replay mechanism for your user inputs.
You want to work around player slot assignment order issues in XInput.
Supported Systems
🛑 Windows Server might work but is not supported 🛑

Bug reports/support requests regarding running on a Server OS will be discarded.

Version 1.16 and below
The driver is built for Windows 7/8.1/10 (x86 and amd64).

Version 1.17 and above
The driver is built for Windows 10/11 only (x86, amd64 and ARM64).

License
The ViGEm Bus Driver is licensed under the BSD-3-Clause, see LICENSE for more information.

How to build
Prerequisites
Step 1: Install Visual Studio 2019
Step 2: Install WDK for Windows 10, version 2004
Step 3: Clone the Driver Module Framework (DMF) into the same parent directory.
Build the DmfK project with Release and Debug configurations for all architectures (x64 and Win32).
You can build directly within Visual Studio.

Do bear in mind that you'll need to sign the driver to use it without test mode.

Contribute
Bugs & Features
Found a bug and want it fixed? Open a detailed issue on the GitHub issue tracker!

Have an idea for a new feature? Let's have a chat about your request on Discord.

Questions & Support
Please respect that the GitHub issue tracker isn't a helpdesk. We offer a range of support resources you're welcome to check out!

Installation
Pre-built production-signed binaries for Windows 10/11 are provided by Nefarius Software Solutions e.U. as an all-in-one setup.

Sponsors
Sponsors listed here have helped the project flourish by either financial support or by gifting licenses:

3dRudder
Parsec
JetBrains
Advanced Installer
ICAROS
Known users of ViGEm
A brief listing of projects/companies/vendors known to build upon the powers of ViGEm.

This list is non-exhaustive, if you'd like to see your project included, contact us!

3dRudder
Parsec
GloSC
UCR
InputMapper
Oculus VR, LLC.
WiimoteHook
XJoy
HP
DS4Windows
XOutput
RdpGamepad
Touchmote
Mi-ViGEm
BetterJoy
Regame
NetInput
NetJoy
