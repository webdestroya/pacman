#!/usr/bin/perl

use Socket;
socket(SOCKET, PF_INET, SOCK_DGRAM, getprotobyname("udp")) or die "socket: $!";

$HOSTNAME = "127.0.0.1";
$PORTNO = 4445;
$DATA1 = 1;
$DATA2 = 45;
$DATA3 = 43;
$DATA4 = 20;

$MSG = pack('UUUU', $DATA1, $DATA2, $DATA3, $DATA4);

print "Message: ".$MSG."\n";

$ipaddr   = inet_aton($HOSTNAME);
$portaddr = sockaddr_in($PORTNO, $ipaddr);
send(SOCKET, $MSG, 0, $portaddr) == length($MSG) or die "cannot send to $HOSTNAME($PORTNO): $!";
