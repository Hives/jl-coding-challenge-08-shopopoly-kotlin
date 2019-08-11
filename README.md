# John Lewis Coding Challenge - Shopopoly

Creating a Monopoly-style game.

## Part 1 - create Location data type

Task is to create a `Location` data type, which can represent "Go", "Free Parking", a factory/warehouse, or a retail site, all of which have certain properties or conditions. The data type should prevent creation of invalid locations as far as possible.

[Full details here.](https://coding-challenges.jl-engineering.net/challenges/challenge-8/)

### Thoughts etc.

- The number of each type is limited - e.g. there can be max 20 retail sites, only 1 free parking etc. I did this by having a property `instances` in each class which is incremented every time a new instance is created. In testing though I had to reset this to 0 in between tests (`Location.Retail.instances = 0`). Is there a better way to do handle that in the tests? And wouldn't it be better if it wasn't possible to reset the `instances` property? Seems a bit shonky
- I only implemented limiting the number of instances for retails and factory/warehouse sites. Should do it for 'Go' and 'Free Parking' too.
- I put the `instance` and `maxInstances` properties in a companion object, since apparently Kotlin can't do class (static) properties and that's what you have to do instead. Is this the best approach? How does inheritance work for companion objects? Would it be possible to declare them in the parent class and override them in the subclass? [This solution](https://coding-challenges.jl-engineering.net/challenge-08/07-kotlin/) seems to be implementing them as methods, not properties. Means you can override them in the subclass. Is that a good approach?
- Should make a `GBP` class to deal with money, to ensure that money values are always > 0.
- It says the groups contain 2 or 3 locations - could we make it so it prevents you adding 4 locations to a group? How would that work?
