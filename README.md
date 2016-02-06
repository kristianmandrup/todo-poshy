# todo

A [re-frame](https://github.com/Day8/re-frame) application designed to ... well, that part is up to you.

## Status

*WIP*

Currently being refactored to use re-frame. Still ways to go. Please help out ;)

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build

```
lein clean
lein cljsbuild once min
```
