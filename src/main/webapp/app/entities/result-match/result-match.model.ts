import { BaseEntity } from './../../shared';

export class ResultMatch implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public matchId?: number,
    ) {
    }
}
