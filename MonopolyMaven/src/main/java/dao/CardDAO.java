package dao;

import java.util.List;

import models.Card;

/**
 * @author Ana
 */
public interface CardDAO {

	/* CRUD operations */
	/* Create */
	public void addCard(Card card);

	/* Read */
	public Card findCardById(int id);

	/* Update */
	public void updateCard(Card card);

	/* Delete */
	public void deleteCard(int id);

	/* Read All */
	public List<Card> getAll();

}
