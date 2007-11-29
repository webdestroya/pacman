#!/usr/bin/perl

# This is a makeshift server to emulate another computer making moves.
# Use this to make sure that the game is behaving the way it should be.

# Packet Info (All joined with |)
# ----------
# Type
#

use Socket;

($port) = @ARGV;
$port = 12207;

$sockaddr = 'S n a4 x8';

print "PacMan server, running on port $port\n";

$this = pack($sockaddr, &AF_INET, $port, "\0\0\0\0");

select(NS);
$| = 1;
select(STDOUT);

socket(S, &PF_INET, &SOCK_STREAM, 0) || die "pacman died: $!\n";
bind(S,$this) || die "Binding Error: $!\n";
listen(S,5) || die "Connect: $!\n";

select(S);
$| = 1;
select(STDOUT);

$con = 0;
print "Waiting for a PacMan client...\n";

for(; ;)
{
	($addr = accept(NS,S)) || die "The client exploded: $!\n";

	$con++;

	if( ($child[$con] = fork()) == 0)
	{
		# fork out to the kid
		print "PacMan connected!\n";

		($af,$port,$inetaddr) = unpack($sockaddr, $addr);
		@inetaddr = unpack('C4', $inetaddr);
		print "Connection Dump: $af $port @inetaddr\n";

		while(<NS>)
		{
			print "Connection: $_";
			$comm = chomp($_);
			@comm = split(/|/, $comm);
			


			print(NS '$_');
		}
		close(NS);
		exit;
	}

	close(NS);

	print "Listening for next connection...\n";
}
