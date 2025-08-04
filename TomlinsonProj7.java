import cardgames.*;
import javax.swing.JOptionPane;

public class TomlinsonProj7
{
    public static void main(String[] args)
    {
        //Making a new window: Setting the amount of cards to show to 2 and stating a deck should appear
        GUI window = new GUI(2, true);
        //^The amount of cards which show initially is the amount you put in the parameter, the top card of the deck should not appear yet
        
        //Making a new deck of cards using the default constructor
        Deck nDeck = new Deck();
        //Shuffling the deck of cards
        //This means that the card is bananas 3x (I saw the add this comment 3 times so I put 3x :) )
        nDeck.shuffleDeck();
        
        //Making the orignal amount that the player starts with to $100 and having it display in the window       
        int amount = 100;
        window.showAmount(amount);
        
        /*
        -->Keeping the previous lines of code separeted from the while loop because there shouldn't be a new window made, a new deck both made and shuffled, 
        or a starting amount at $100 unless the user started over entirely and didn't pick up from their last game to keep going
        */
        
        //Making a boolean for the while loop and setting it to true
        boolean game = true;
        //Making a loop which runs as long as game isn't false (meaning the user doens't want to play anymore) and the user still has money
        while(game!=false && amount!=0)
        {
            //Dealing 2 cards from the deck and storing them in their respective variables
            Card cardOne = nDeck.dealCard();
            Card cardTwo = nDeck.dealCard();  
            //Having the same 2 cards show in the GUI (window) in order of least to greatest
            if(cardOne.getValue()<cardTwo.getValue())   //Checking if the first card value is less than the second
            {
                window.showCard(cardOne);               //Putting the cards into the window in order based on their values
                window.showCard(cardTwo);
            }
            else                                        //If not then the second card gets put in the window first
            {
                window.showCard(cardTwo);
                window.showCard(cardOne);
            }

            //Determining if the user wants to bet before, between, or after
            String userResponse = JOptionPane.showInputDialog("Whats your guess? \nEnter 0 for before, 1 for between, or 2 for after");
            //Parsing the guess entered by the user to an int for later purposes
            int BBA = Integer.parseInt(userResponse);
            if(!(0<=BBA) || !(BBA<=2))  //Incase the user doesn't enter a number from 0-2 inclusive
                userResponse = JOptionPane.showInputDialog("Please enter either 0 for before, 1 for between or 2 for after");
            BBA = Integer.parseInt(userResponse);
            
            //Getting the users bet amount
            String userBet = JOptionPane.showInputDialog("How much are you going to bet?");
            //Parsing the bet entered by the user so it becomes a double
            double bet = Double.parseDouble(userBet);
            if(bet>amount)  //Making sure the user doesn't enter a value greater than what they have
                userBet = JOptionPane.showInputDialog("Please enter a bet thats not greater than the amount you have");
            bet = Double.parseDouble(userBet);
            //Having the bet placed by the user show in the window
            window.showBet(bet);

            //Making a new card which is the card the user bets on
            Card cardThree = nDeck.dealCard();
            //Having the new card show up as the card from the deck in the window
            window.showDeckCard(cardThree);

            //Determining if the User won the game or not
            int cThreeVal = cardThree.getValue();                       //Getting the value of the card the user bet on
            boolean gResult = false;                                    //gResult --> Game Result, setting it to false for now
            boolean between = cardThree.betweenCards(cardOne, cardTwo); //Calling the betweenCards method on the third card dealt from the deck to be used later
            if(BBA == 0) //Meaning the user guessed before
            {
                if(cardOne.getValue()< cardTwo.getValue())              //Comparing the values of the two cards initially dealt at the beginning of the game
                {
                    if(cThreeVal < cardOne.getValue())                  //Since the user guess before, this checks if the card they bet on comes before the smaller of the two cards
                    {
                        gResult = true;                                 //If the user was right then the boolean becomes true
                        window.showMessage("You won!");                 //Showing a message for the user to let them know they won
                    }
                    else                            
                    {
                        gResult = false;                                //If the third card drawn wasn't smaller than the first card then the player lost
                        window.showMessage("You lost :(");              //The losing message also gets returned
                    }
                }
                else if(cardOne.getValue() > cardTwo.getValue())        //Same as before but instead the second card of the initial 2 is the smaller one and gets compared to the 3rd
                {
                    if(cThreeVal < cardTwo.getValue())                  //Seeing if the third card drawn is still the smaller one
                    {
                        gResult = true;                                 //If it is then the boolean becomes true and a winning message pops up
                        window.showMessage("You won!");
                    }
                    else
                    {
                        gResult = false;                                //If not, then the boolean is set to false and a loosing message gets displayed in the window
                        window.showMessage("You lost :(");
                    }
                }
            } //End of the user guessing before
            else if(BBA == 1) //Meaning the user guessed between
            {
                if(between)                                     //Using the boolean which called the betweenCards method for the if statement
                {
                    gResult = true;                             //If the third card dealt is between the 2 then the game result is true and a winning message pops up for the user
                    window.showMessage("You won!");
                }
                else
                {
                    gResult = false;                            //If not then the game result is false and the user is told they lost
                    window.showMessage("You lost:(");
                }
            } //End of user guessing between
            else if(BBA == 2) //Meaning the user guessed after
            {
                if(cardOne.getValue() > cardTwo.getValue())     //Finding the bigger card or the one with the bigger value between the two initiially dealt cards
                {
                    if(cThreeVal > cardOne.getValue())          //Comparing the third card dealt with the bigger card of the two
                    {
                        gResult = true;                         //If the value of the third card is greater then the game result is true and the player won
                        window.showMessage("You won!");
                    }
                }
                else
                    if(cThreeVal > cardTwo.getValue())          //Comparing values again :)
                    {
                        gResult = true;                         //Same as before, if the 3rd card dealt is bigger then the game result is true and the player wins
                        window.showMessage("You won!");
                    }
                else
                    {
                        gResult = false;                        //If not then the game result is false and the user is told that they lost
                        window.showMessage("You lost:(");
                    }           
            }

            //Determining the amount of money the user has after betting
            if(gResult) //Meaning the user guessed correctly and gained money
            {
                //Doing *2 because the user gains back what they put down as a bet and the amount they bet, they put
                //down 5 as a bet so they'll get back the 5 and another 5 since thats what they bet and were right
                double gain = bet*2; 
                amount+=gain;       //Adding what the user gained to the amount
                window.showAmount(amount);  //Having the updated amount show up in the window
            }
            else //The user guess incorrectly and lost money
            {
                double loss = bet*2;    //Same as before but since the user guessed incorrectly they lose money instead
                amount-=loss;           //Subtracting the loss from the total amount
                window.showAmount(amount);  //Showing the updated amount in the window
            }
            
            if(amount<=0)           //Checking if the user ran out of money which would mean their game ends
            {
                System.out.print("You ran out of money and can no longer play, "); //Letting the user know they ran out of money
                game = false;
            }
            else
            {
                String userGame = JOptionPane.showInputDialog("Do you want to play again? \nEnter Yes or No"); //If they didn't run out of money then they are asked if they want to play again
                if(!userGame.equalsIgnoreCase("Yes") && !userGame.equalsIgnoreCase("No"))
                    userGame = JOptionPane.showInputDialog("Please enter yes or no");
                if(userGame.equalsIgnoreCase("Yes"))    
                {
                    game = true;                //If the user wants to play again then the game is set to true so the while loop continues
                    window.clearDeckCard();     //The game gets reset by using this and the following method which clears the deck and the 2 cards dealt by the user
                    window.clearPlayerCards();
                }
                else                            //If not and the user chose to end the game
                {
                    System.out.print("You chose not to play again, ");  //There's a printed statement which lets the user know they chose to opt out of the game
                    game = false;                                       //The game is set to false so the loop ends
                }
            }
        }  
        System.out.println("Thanks for playing :)");     //A little thank you for playing message because why not :) 
    }
}