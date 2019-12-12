import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ItemVendaDetailComponent } from 'app/entities/item-venda/item-venda-detail.component';
import { ItemVenda } from 'app/shared/model/item-venda.model';

describe('Component Tests', () => {
  describe('ItemVenda Management Detail Component', () => {
    let comp: ItemVendaDetailComponent;
    let fixture: ComponentFixture<ItemVendaDetailComponent>;
    const route = ({ data: of({ itemVenda: new ItemVenda(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ItemVendaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ItemVendaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemVendaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemVenda).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
