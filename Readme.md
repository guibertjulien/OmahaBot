TODO :

- scan 5 <--> 6
- scan 10 card
- Ne joue pas
- empty

ThreadDeal 1s : #handId
-----------------------------
- handId
- listMyCard
- myStack
- SB, BB, TournamentStep
- positionButton
- nbPlayerTable, myPostionTable : utile pour bluff
- nbPlayerTournament, 

ThreadDealStep 100ms : #dealStep
-----------------------------
- dealStep (PRE_FLOP, FLOP, TURN, RIVER, UNKNOW)
- listBoardCard

ThreadPot 100ms : #potAmount
-----------------------------
- potAmount

ThreadPlayer 100ms : #positionActivePlayer
-----------------------------
- positionActivePlayer
- nbPlayer
- lastAction (FOLD, CHECK, BET, RAISE, RERAISE, ALLIN)
Si bet, lastBet


- ThreadDeal : 1s
- ThreadDealStep : 100ms
-- ThreadPot : 100ms
-- ThreadPlayer : 100ms
		
		
SYNCHRONISATION

MULTITABLE
--> Multi ThreadTounament
