import { BaseEntity } from './../../shared';

const enum GameMode {
    'SLAYER',
    'FLAGS',
    'OBJECTIVE'
}

export class MatchMode implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public timeToWin?: number,
        public scoreToWin?: number,
        public gameMode?: GameMode,
        public matchId?: number,
    ) {
    }
}
