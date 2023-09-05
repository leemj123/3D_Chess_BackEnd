package com.gamza.chess.service.chessService;

import com.gamza.chess.Enum.Color;
import com.gamza.chess.piece.*;
import org.springframework.stereotype.Service;

@Service
public class ChessBoardSetting {
    private int Piece_Id_Count;
    public Piece[][] setting(int MaxOfX, int MaxOfY){
        Piece_Id_Count = 1;
        Piece[][] board = new Piece[MaxOfX][MaxOfY];

        for (int y = 0; y < MaxOfY; y++) {
            switch (y) {
                case 0:
                    board[y] = setFirstLineOfPiece(MaxOfX, y, Color.White);
                    break;
                case 1:
                    board[y] = setSceondLineOfPiece(MaxOfX, y, Color.White);
                    break;
                case 6:
                    board[y] = setSceondLineOfPiece(MaxOfX, y, Color.Black);
                    break;
                case 7:
                    board[y] = setFirstLineOfPiece(MaxOfX, y, Color.Black);
                    break;
                case 2: case 3: case 4: case 5:
                    break;
            }
        }

        return board;
    }

    private Piece[] setFirstLineOfPiece(int MaxOfX,int currentY, Color color) {
        Piece[] pieces = new Piece[MaxOfX];

        for (int x = 0; x < pieces.length; x++) {
            switch (x) {
                case 0: case 7:
                    pieces[x] = new Rook(Piece_Id_Count,color,x,currentY);
                    Piece_Id_Count++;
                    break;
                case 1: case 6:
                    pieces[x] = new Knight(Piece_Id_Count,color,x,currentY);
                    Piece_Id_Count++;
                    break;
                case 2: case 5:
                    pieces[x] = new Bishop(Piece_Id_Count,color,x,currentY);
                    Piece_Id_Count++;
                    break;
                case 3:
                    pieces[x] = new Queen(Piece_Id_Count,color,x,currentY);
                    Piece_Id_Count++;
                    break;
                case 4:
                    pieces[x] = new King(Piece_Id_Count,color,x,currentY);
                    Piece_Id_Count++;
                    break;
            }
        }
        return pieces;
    }

    private Piece[] setSceondLineOfPiece(int MaxOfX,int currentY,Color color) {
        Piece[] pieces = new Piece[MaxOfX];

        for (int x=0; x < MaxOfX; x++) {
            pieces[x] = new Pawn(Piece_Id_Count,color,x,currentY);
            Piece_Id_Count++;
        }
        return pieces;
    }
}
