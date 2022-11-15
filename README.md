# Welcome to Pandamaniacs (FRC Team 1293)

Helpful links:
  - [FRC rules 2022](https://firstfrc.blob.core.windows.net/frc2022/Manual/2022FRCGameManual.pdf)
 - [GitHub desktop](https://desktop.github.com/)
 - [Git command line help](https://gist.github.com/davfre/8313299)
# What is GitHub, why do we use it, and how to use it
GitHub is used for file version control. Every change ever can be viewed, restored.

## Typical workflow
This is the 95% workflow for working with Git, all can be done through 
1. Check out a branch from main, 
	a. `git checkout main && git reset --hard && git checkout -b <whatever your branch name is>`, this checks out `main`, clears out locally saved changes of that branch, checks out a new branch off of main
2. \<do work>, save the file(s)
3. Stage changes for the commit
	a. `git add <filename>` to stage a single file,  `git add -A` to stage the entire repo
4. create a commit
	a. `git commit -m "this is my commit message describing what my changes do"`
5. push commit to git
	a. `git push origin` to push your local changes to git
6. create a pull request on the web UI or desktop
7. When the pull request is approved, merge the commit to main, delete the branch you were working on (optional, but you can have a maximum number of branches)

## Git etiquette

1. When you make a branch, either in the desktop, web UI, or command line, name the branch with your initials so we can tell who the branch belongs to quickly.
	a. `git checkout -b jg-driverController` would create a branch called 'jg-driverController'
	
2. The repo has a rule that every pull request needs at least one approver before it can be merged to `main`. This is to protect what is considered the "latest" from being overwritten due to miscommunication or accidentilly making changes on main when you meant to do it on your own branch. You an always revert (remove) that change, but this branch protection will eliminate most reasons to do that.
