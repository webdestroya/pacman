#!/usr/bin/perl

use Socket;
socket(SOCKET, PF_INET, SOCK_DGRAM, getprotobyname("udp")) or die "socket: $!";

%PTYPES = (
	# The actual commands
	JOIN=>'0',
	GTYPE=>'1',
	GMOVE=>2,
	PMOVE=>3,
	GAMEOVER=>4,
	LEAVE=>5,
	GAMEFULL=>6,
	GAMESTART=>7,
	ACK=>8,	
	ERROR=>9,
	
	# The ghosts
	BLINKY=>'0',
	CLYDE=>1,
	INKY=>2,
	PINKY=>3,
	
	# The keyboard directions
	UP=>'0',
	DOWN=>1,
	LEFT=>2,
	RIGHT=>3,

	# errors
	UNKNOWN_COM=>'0',
);

$HOSTNAME = "127.0.0.1";
$PORTNO = 4445;
$DATA1 = 0;
$DATA2 = 0;
$DATA3 = 0;
$DATA4 = 0;


if( $#ARGV>=0 )
{
	$DATA1 = $PTYPES{ $ARGV[0] };
}
if( $#ARGV>0 )
{
	$DATA2 = $PTYPES{ $ARGV[1] };
}
if( $#ARGV>1 )
{
	$DATA3 = $PTYPES{ $ARGV[2] };
}
if( $#ARGV>2 )
{
	$DATA4 = $PTYPES{ $ARGV[3] };
}

$MSG = pack('CCCC', $DATA1, $DATA2, $DATA3, $DATA4);

print "Message: [".$DATA1."|".$DATA2."|".$DATA3."|".$DATA4."]\n";
print "Message: ".$MSG."\n";

$ipaddr   = inet_aton($HOSTNAME);
$portaddr = sockaddr_in($PORTNO, $ipaddr);
send(SOCKET, $MSG, 0, $portaddr) == length($MSG) or die "cannot send to $HOSTNAME($PORTNO): $!";


$portaddr = recv(


