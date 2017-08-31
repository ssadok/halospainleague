import { BaseEntity } from './../../shared';

export class Match implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public duration?: number,
        public resultMatchId?: number,
        public teamListId?: number,
        public tournamentId?: number,
        public mapId?: number,
        public matchModes?: BaseEntity[],
    ) {
    }
}
