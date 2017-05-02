# Lifelink

This project aims to be an android application that covers the weak spots of state-of-the-art MTG life counter apps.

A life counter for Magic: the Gathering is a tool players use to keep track of their life totals. Most often, 2-4 players all huddle around a single screen with 2-4 buttons per player (+1, -1, +5 etc.). This clutters the screen with multiple buttons that do the same thing albeit to different players life totals. Lifelink solves this by having each participant use their own phone and then *connecting locally* so that every player can see everyone's life total, but only have the buttons they are supposed to use.

![alt text](https://github.com/johnnil/Lifelink/blob/master/Lifelink%20mockup.png)

___

## Possible features

Planned features:
* Customizable background (single colour)
* Visible username
* Multiple buttons for different increments

Suggestions (if time allows):
* Poison counters
* Commander damage
* Turn tracker
* Recursive life loss (Phyrexian Arena etc.)
* Chess clock

___

## Branching structure and workflow

This is the branching structure and work flow that we have decided to use for this project. We used this [guide](http://nvie.com/posts/a-successful-git-branching-model/) by Vincent Driessen when creating this structure.

### Main branches

We have two **main branches** with **infinite** lifespan: **master** and **develop**.

#### master branch

In **origin/master** the HEAD source code should reflect a production-ready state. The most polished and presentable version of our app that should always be in this branch. We should update **master** in larger sophisticated updates similar to patches.

#### develop branch

In **origin/develop** the HEAD source code should reflect the latest development progresses that we have been made that we eventually want to patch into **master**. When the changes added to **develop** has become stable enough and we **both** have **reviewed** the changes we add the changes to **master** in the form of a patch tagged with a release number.

Development with this branch structure is nicely represented in this image:

![Borrowed from the article](http://nvie.com/img/main-branches@2x.png)

### Supporting branches

The **supporting branches** are where the actual development and maintenance of the two main branches takes place. There are three types of supporting branches that we use: **feature branches**, **release branches** and **hotfix branches**.

#### Feature branches

The **feature branch** is a branch that is used when you want to add a specific feature or make a specific change to the code in the **develop** branch.

When you want to add a specific feature or make a specific change you create a *temporary* branch for that specific feature/change and name it something appropriate. You push the changes to this branch and test that they work properly with the code currently in the **develop** branch. When you want the code to be merged you have to do a **pull request** first and then depending on the size of the changes a second opinion review should be requested and performed before the code is merged with **develop** to ensure that nothing will break.  Alternatively the feature branch turns out to be a failed feature and is removed. In either case the branch lifespan is **temporary**.

Here is a good picture that illustrates the lifespan of a **feature branch** and it's relationship with **develop**.

![Borrowed from the article](http://nvie.com/img/fb@2x.png)

There are also some good instructions in the article about how to use certain git commands to make the handling of the **feature branch** easier that I think we should use.

#### Release branches

The **release branch** is a branch type that is used when we want to gather all of the features that we wish to be contained in a specific release. The terminology "release" maybe isn't very relevant in our project and maybe "patch" would be more fitting but we think that it would be a good idea to use as it's probably used in a professional environment. It's a useful branch as it allows for us to continue adding features and making changes to **develop** without affecting the contents a specific scheduled release.

For **release branches** we our naming convention is "release-X" where X is the release number.

After a **release branch** is branched off from **develop** no more commits from the **develop** branch should be made to the **release branch**. The only changes that should be made on the **release branch** are possible bug fixes after some extended testing. The bug fixes can then also be pushed to the **develop** so that branch also gets updated. When it's time to patch the update you merge the **release branch** with **master** and **develop**.

There are also some good instructions in the article about how to use certain git commands to make the handling of the **release branch** easier that I think we should use.

#### Hotfix branches

**Hotfix branches** are branches made for fixing important bugs that have been detected on **master** that need quick fixing. They ensure that regular development can be pursued on **develop** while a quick fix is made simultaneously.

 The naming convention for hotfixes uses the same number as the release number: "hotfix-X" where X is the release number.

A **hotfix branch** is branched off from **master**. Changes are then made to the **hotfix branch** that are then tested and reviewed before merging the **hotfix branch** with **master** and **develop**.

Here is another good illustration from the article. It illustrates the lifespan of a **hotfix branch**:

![Borrowed from the article](http://nvie.com/img/hotfix-branches@2x.png)

In the same case as the other supporting branches, there exists good instructions for specific git commands in the article for
 **hotfix branches**.

### Summary

Here is a good illustration that shows how the different branches work and relate to each other as well as showing their lifespans:

![Borrowed from article](http://nvie.com/img/git-model@2x.png)
