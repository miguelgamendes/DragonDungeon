//
//                                                `                                     
//                                               `#                                     
//                                              `@`                                     
//                                             `@.                                      
//                                            .#.                                       
//                                           :#.                                        
//                                         .#+.`.``                                     
//                                       .#'''#++++#.                                   
//                                     `#'''''++++++#;                                  
//                                    ,''''''++++++###:                                 
//                                   .''''''++++++#####`                                
//                                   #''''+++++++######.                                
//                                  `''''++++#+'#######'                                
//                                ,#+'''+++++`   ;#####+                                
//                               `+''''+++#,      @####'                                
//                              `+'''+++#'`       #####:                                
//                             '''''+: `          @####`                                
//                             +'''+;            .###@@                                 
//                             #'++,             @#@@@,                                 
//                             ,#               '@@@@@                                  
//                              ;              ,@@@@@.                                  
//                                            .@@@@@,       ``                          
//                                           .@@@@@.    `.#@;`                          
//                                          .@@@@@`  `,#@@@`                            
//                                         ;@@@@, `,@@@@@#                              
//                                       `@@@@+``+@@@@@@@                               
//                                      .@@@@.`'@@@@@@@@`                               
//                                     :@@@'`,@@@@@@@@@+                                
//                                   `#@@@.`@@@@@@@@@@@`                                
//                                  .@@@; ,@@@@@@#,``                                   
//                                 ,@@@.`#@@@@#.`      `.'@@@;`                         
//                                '@@; `@@@@;`     `,#@@@@+`                            
//                               '@@` ,@@@,     `,@@@@@@@`                              
//                              ;@;  :@@:     .#@@@@@@@;                                
//                             ,@:  '@@`   `;@@@@@@@@@:                                 
//                            .@;  +@'   `+@@@@@@@@@@+                                  
//                            @#  '@,  `+@@@@@@@@@@@@`                                  
//                           '@` :@.  :@@@@@@@@@#';;:                                   
//                          `@. ,@. `@@@@@@'.`                                          
//                          #' .@. `@@@@'`                                              
//                         `@``@: .@@@:          ```....,.`                             
//                         +, @@ ,@@;`     `,+@@@@@@@@@@+,`                             
//                        `@ :@`;@@`   `,#@@@@@@@@@@@@,                                 
//                        ,. @.#@'  `,@@@@@@@@@@@@@@@.                                  
//                        @ .`@@. `'@@@@@@@@@@@@@@@@,                                   
//                       `: @@@``'@@@@@@@@@@@@@@@@@@                                    
//                       .. '@`,@@@:.``     ``.;#@@.                                    
//                       '``@`'@;`                .                                     
//                       @ +:@;`          `,`                                           
//                       ' @:`        .:@@@.                                            
//                                  :@@@@@.                                             
//                       +`       :@@@@@@@.      ``..,,,,..`                            
//                       .#    `:@@@@@@@@@@#';+@@@@@@@@@@@@@@#,                         
//                        #@':'@@@@@@@@@@@@@@@@@@@@@@#,.``.,;#@@.                       
//                         +@@@@@@@@@@@@@@@@@@@@@@:`            `                       
//                          ,@@@@@@@@@@@@@@@@@+.`                                       
//                           `:@@@@@@@@@@@#,`                                           
//                              `.:;;:,.`                                               
//
import maze.Labyrinth;

public class Dungeon{
    public static void main(String[] args){
        Labyrinth lab = new Labyrinth();

        //game parameters definition
        lab.initialParameters();

        //generate map
        lab.generateRandomMap();

        //main game cycle
        lab.gameCycle(lab);
    }
}