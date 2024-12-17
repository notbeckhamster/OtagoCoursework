start:
	ldb #48
displayLoop:
	stb $0400
	incb
	cmpb #57
	bls displayLoop
	rts