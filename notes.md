git fetch origin pull/<number>/head:pr-<number>
git switch pr-<number>
git remote add <name> <url>

make changes, commit

git push <name> pr-<number>:<remote_branch>