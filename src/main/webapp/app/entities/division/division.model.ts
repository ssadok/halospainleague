import { BaseEntity } from './../../shared';

const enum DivisionType {
    'GOLD',
    'SILVER',
    'BRONCE'
}

export class Division implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public maxPlayers?: number,
        public divisionType?: DivisionType,
        public teams?: BaseEntity[],
    ) {
    }
}
