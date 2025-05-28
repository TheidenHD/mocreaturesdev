---
name: Bug report
about: Report something that is not working in the mod
title: ''
labels: bug
assignees: ''

---

This is the repository of Mo' Creatures Extended exclusively for 1.12.2 and is not affiliated with other forks or other unofficial ports of Mo' Creatures. Please do not report any bugs originating from other forks or unofficial ports of Mo' Creatures, they will immediately be closed and marked as invalid.

Make sure that the bug you are reporting happens on the LATEST version of the mod, especially if you are playing on a modpack that hasn't been updated in awhile. It is likely that the bug you are reporting has already been fixed and will also count as an invalid issue.

**If you are making an issue regarding entity spawns, please attempt the following before reporting:**

1. Adjust the frequency values inside `MoCreatures.cfg`.
2. Utilize the Spawn Caps tweak in [Universal Tweaks](https://www.curseforge.com/minecraft/mc-mods/universal-tweaks) to further increase entity spawn caps.
3. If you are playing with other mods that add entities and they are not spawning, you will most likely have to adjust their weights using a mod like [BiomeTweaker](https://www.curseforge.com/minecraft/mc-mods/biometweaker) because of how vanilla's weight system works.
4. Be sure to mess around with the `World Gen Spawning Creatures` and `World Gen Spawning Water Creatures` config options inside `MoCSettings.cfg`.

**If you are making an issue regarding item drops:**
Item drops were overhauled in Extended, hearts and eggs were made to be rarer drops rather than dropping almost always. Because of these  changes, it will be confusing if you played with the original Mo'  Creatures mod. It is recommended to use [Just Enough Resources](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer) to see drop rates and [GroovyScript](https://www.curseforge.com/minecraft/mc-mods/groovyscript) or [LootTweaker](https://www.curseforge.com/minecraft/mc-mods/loottweaker) to adjust the loot tables to your liking. Only make an issue if you are **absolutely** sure there is a bug going on.