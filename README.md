# Checkvist Focus [![Build Status](https://travis-ci.com/Heapy/checkvist-focus.svg?branch=master)](https://travis-ci.com/Heapy/checkvist-focus)

Displays only tasks marked with `#Focus` tag.

## How to use?

I'm running this app every 10 minutes:

```bash
(crontab -l; echo "1,11,21,31,41,51 * * * * export CHECKVIST_ID=42; export CHECKVIST_USERNAME=username; export CHECKVIST_PASSWORD=password; /home/user/path/to/checkvist-focus/bin/checkvist-focus > /home/user/path/to/todo/file") | crontab -
```

And `cat` todo file in terminal on start:

```bash
# add this line to ~/.bashrc

cat /home/user/path/to/todo/file;
```
