#!/usr/bin/perl

# Use this to test server shenanigans

use Socket;
$them = 'localhost';
$port = 12207;

$SIG{'INT'} = 'dokill';
sub dokill {kill 9,$child if $child}

$sockaddr = 'S n a4 x8';

($name,$aliases,$type,$len,$thataddr) = gethostbyname($them);
$that = pack($sockaddr, &AF_INET, $port, $thataddr);

if( socket(S,&PF_INET, &SOCK_STREAM, 0))
{
	print "socket conn ok\n";
}
else
{
	die "i died: $!\n";
}

if( connect(S, $that) )
{
	print "connect ok\n";
}
else
{
	die "i died: $!\n";
}


select(S);
$| = 1;
select(STDOUT);

if($child = fork)
{
	while(<STDIN>)
	{
		print S;
	}
	sleep 1;
	do dokill();
}
else
{
	while(<S>)
	{
		print;
	}
}


