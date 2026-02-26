# Git, DevOps & CICD

## Terminal History

### Step 1 - Initialize & Configure Git
git config --global user.name "Celine Ingabire"
git config --global user.email "ingabireceline54@gmail.com"
git config --global init.defaultBranch main
git init
git add README.md
git commit -m "chore: initial project setup"

### Step 2 - Connect to GitHub
git remote add origin https://github.com/Celxine/git-devops-cicd.git
git push -u origin main

### Step 3 - Create dev branch
git checkout -b dev
git push -u origin dev

### Step 4 - Create and delete test branch
git checkout -b test
git push -u origin test
git checkout dev
git branch -d test
git push origin --delete test

### Step 5 - Create feature branch
git checkout -b ft/setup

### Step 6 - Add test.java and commit
git add test.java
git commit -m "feat: add test.java with setup validation placeholder"

### Step 7 & 8 - Stash and restore
git stash save "wip: adding database setup comment"
git stash list
git stash pop stash@{0}

### Step 10 - Merge conflict resolution
git merge main
git add test.java
git commit -m "fix: resolve merge conflict between ft/setup and main"
git push origin ft/setup