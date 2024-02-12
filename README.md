# Jake Andersen-Lum Personal Project

I plan to design my project to digitize the game
system **Roll for Shoes**, which can be found at this
[link](https://rollforshoes.com/). Roll for Shoes
is a simplified tabletop role-playing game (TTRPG),
a genre exemplified and characterized by Dungeons
and Dragons. Unlike D&D, however, Roll for Shoes
is designed to be lightweight and simple, to allow
groups of friends to jump right into the process
of storytelling.

## What will the Application Do?

Roll for Shoes is a fairly simple system, outlined
in the following six rules:

1. Say what you do and roll a number of 
**D6s**—6-sided dice—determined by the level of 
relevant skill you have.
2. If the sum of your roll is **higher** than the 
opposing roll, the thing you wanted to happen, 
happens.
3. At start, you have only one skill: **Do 
Anything 1**.
4. If you roll **all sixes**, you get a **new skill** 
specific to the action, **one level higher** than 
the one you used.
5. For every roll you fail, you get **1 XP**.
6. XP can be used to change a die into a 6 _for 
advancement purposes only_.

My application will **automate** and **assist** with
these rules to streamline the game. It cannot and
does not take the place of the Game Master or 
narrator.

## Who will Use this Application?

This application will be used by **players**. The
Game Master will describe the environment or
situation, and the players will respond following 
the rules of the game outlined above. Using my 
program, they can easily **roll** for each of their
skills, be automatically **prompted** to create new
skills, be **reminded** about their available XP, 
and **view** their skill tree.

## Why is this Project of Interest to Me?

I've always had an interest in TTRPGs, and I wanted
to somehow incorporate that into my term project.
The game that I've played most often, though, is D&D
which has a **320-page** rulebook that far outstrips
the scope of this course. I stumbled across the Roll
for Shoes system listening to one of my favorite
podcasts, and it struck me as being both incredibly
**simple rules-wise** and incredibly **rich 
code-wise**, which made it the **perfect** system
for this project.

## User Stories

- As a user, I want to be able to create a new skill
and add it to the skill tree when I roll all 6s on a
skill check.
- As a user, I want to be able to spend XP to
automatically create a new skill and add it to the
skill tree.
- As a user, I want to be able to view all the
skills in my skill tree, as well as my available XP.
- As a user, I want to be able to roll dice for
any of my skills and view the sums of those rolls.
- As a user, I want to be able to compare my skill
checks to a set difficulty level, and earn XP on a
failure.
- As a user, I want to have the option to save my 
skills and skill tree to file.
- As a user, I want to have the option to load my 
skills and skill tree from file.

## Phase 4: Task 2
Thu Nov 30 20:10:15 PST 2023<br>
New Skill, "Do Anything", created<br>
Thu Nov 30 20:10:18 PST 2023<br>
1 dice rolled for Do Anything<br>
Thu Nov 30 20:10:20 PST 2023<br>
1 XP earned<br>
Thu Nov 30 20:10:27 PST 2023<br>
New Skill, "Kick", created<br>
Thu Nov 30 20:10:27 PST 2023<br>
Kick added to Do Anything sub-skill tree<br>
Thu Nov 30 20:10:27 PST 2023<br>
1 XP spent<br>

## Phase 4: Task 3

I could refactor the code by consolidating the various button classes—SaveButton, RollSkillButton, ViewTreeButton, 
InstructionButton, LoadButton, and MenuButton—into a single class. Currently, these buttons are implemented as 
subclasses of MenuButton. However, given the minimal variation among them, a unified class approach seems more 
efficient and maintainable. On another note, I could try applying the Composite Pattern to my Skill and SkillTree 
classes, since they inherently form a tree structure. Alternatively, I could even integrate the SkillTree 
functionality directly into the Skill class by giving the Skill class a field of a list of Skills, effectively merging 
the two concepts.