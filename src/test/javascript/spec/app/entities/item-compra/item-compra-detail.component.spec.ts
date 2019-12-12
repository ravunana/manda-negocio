import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ItemCompraDetailComponent } from 'app/entities/item-compra/item-compra-detail.component';
import { ItemCompra } from 'app/shared/model/item-compra.model';

describe('Component Tests', () => {
  describe('ItemCompra Management Detail Component', () => {
    let comp: ItemCompraDetailComponent;
    let fixture: ComponentFixture<ItemCompraDetailComponent>;
    const route = ({ data: of({ itemCompra: new ItemCompra(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ItemCompraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ItemCompraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemCompraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemCompra).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
