package com.gamza.chess.service.gameservice.oldchess;

import com.gamza.chess.piece.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChessBoardSetting {
    private int Piece_Id_Count;
    public List<Piece> setting(int MaxOfX, int MaxOfY){
        Piece_Id_Count = 1;
        List<Piece> pieceList = new ArrayList<>();

        for (int y = 0; y < MaxOfY; y++) {
            switch (y) {
                case 0: case 7:
                    pieceList.addAll(setFirstLineOfPiece(MaxOfX, y));
                    break;
                case 1: case 6:
                    pieceList.addAll(setSceondLineOfPiece(MaxOfX, y));
                    break;
                case 2: case 3: case 4: case 5:
                    break;
            }
        }

        return pieceList;
    }

    private List<Piece> setFirstLineOfPiece(int MaxOfX,int currentY) {
        List<Piece> pieceList = new ArrayList<>();

        for (int x = 0; x < MaxOfX; x++) {
            switch (x) {
                case 0: case 7:
                    pieceList.add(new Rook(Piece_Id_Count,x,currentY));
                    Piece_Id_Count++;
                    break;
                case 1: case 6:
                    pieceList.add(new Knight(Piece_Id_Count,x,currentY));
                    Piece_Id_Count++;
                    break;
                case 2: case 5:
                    pieceList.add(new Bishop(Piece_Id_Count,x,currentY));
                    Piece_Id_Count++;
                    break;
                case 3:
                    pieceList.add(new Queen(Piece_Id_Count,x,currentY));
                    Piece_Id_Count++;
                    break;
                case 4:
                    pieceList.add(new King(Piece_Id_Count,x,currentY));
                    Piece_Id_Count++;
                    break;
            }
        }
        return pieceList;
    }

    private List<Piece> setSceondLineOfPiece(int MaxOfX,int currentY ) {
        List<Piece> pieceList = new ArrayList<>();
        for (int x=0; x < MaxOfX; x++) {
            pieceList.add(new Pawn(Piece_Id_Count,x,currentY));
            Piece_Id_Count++;
        }
        return pieceList;
    }
}
