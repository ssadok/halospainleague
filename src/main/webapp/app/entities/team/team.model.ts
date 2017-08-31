import { BaseEntity } from './../../shared';

const enum TournamentType {
    'ONE_VS_ONE',
    'TWO_VS_TWO',
    'THREE_VS_THREE',
    'FOUR_VS_FOUR',
    'FIVE_VS_FIVE',
    'SIX_VS_SIX'
}

export class Team implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public coverContentType?: string,
        public cover?: any,
        public profileContentType?: string,
        public profile?: any,
        public web?: string,
        public experience?: number,
        public ping?: number,
        public tournamentType?: TournamentType,
        public created?: any,
        public wins?: number,
        public losses?: number,
        public streak?: boolean,
        public position?: number,
        public premium?: boolean,
        public players?: BaseEntity[],
        public teamLists?: BaseEntity[],
        public divisionId?: number,
        public torunaments?: BaseEntity[],
    ) {
        this.streak = false;
        this.premium = false;
    }
}
