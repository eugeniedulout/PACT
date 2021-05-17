package com.example.testmenu;

import android.util.Log;

import java.util.ArrayList;

public class ImportRecipe {
    private ArrayList<String> ingredients = new ArrayList<>();
    private ArrayList<String> produitsName = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<ArrayList<String>> produitsNameP = new ArrayList<ArrayList<String>>();
    private  int n;

    public ImportRecipe(ArrayList<String> ingredients, ArrayList<Product> products) {
        this.ingredients = ingredients;

        this.products = products;
        for(int i=0; i<products.size(); i++)
            this.produitsName.add(products.get(i).getName());

        n = produitsName.size();
        for (int i = 0; i < n; i++) {
            String[] productSplit = produitsName.get(i).split(" ");
            ArrayList<String> productToAdd = new ArrayList<String>();
            for (String nameProduct : productSplit)
                if (nameProduct.length() >= 3 && !Character.isDigit(nameProduct.charAt(0)) && !nameProduct.toLowerCase().equals("des") && !nameProduct.toLowerCase().equals("les") && !nameProduct.toLowerCase().equals("aux") && !nameProduct.toLowerCase().equals("une")) {
                    if (nameProduct.toLowerCase().substring(0, 1) == "l'" || nameProduct.toLowerCase().substring(0, 1) == "d'" || nameProduct.toLowerCase().substring(0, 1) == "s'")
                        productToAdd.add(nameProduct.toLowerCase().substring(2, nameProduct.length() - 1));
                    if(nameProduct.charAt(0) == '('){
                        productToAdd.add(nameProduct.toLowerCase().substring(1, nameProduct.length() - 1));
                        break;
                    }
                    if(nameProduct.charAt(nameProduct.length() -1) == ')') {
                        productToAdd.add(nameProduct.toLowerCase().substring(0, nameProduct.length() -2));
                        break;
                    }
                    productToAdd.add(nameProduct.toLowerCase());


                }
            produitsNameP.add(productToAdd);
        }
        n = produitsNameP.size();

    }

    public ArrayList<Product> getImportProducts() {
        ArrayList<Product> resultat = new ArrayList<Product>();

        int n = produitsName.size();
        int m = ingredients.size();
		/*
		for(int i=0; i<n ; i++) {
			for(int j=0; j<m; j++) {
					System.out.println(ingredients.get(j).toLowerCase());
					System.out.println(produitsName.get(i).toLowerCase());
					int ingredientSize = ingredients.get(j).length();
					int prouctSize = produitsName.get(i).length();

						if(ingredientSize > prouctSize)
							if(ingredients.get(j).toLowerCase().contains(produitsName.get(i).toLowerCase())) {
								resultat.add(produitsName.get(i));
								break;
							}
						else
							if(produitsName.get(i).toLowerCase().contains(ingredients.get(j).toLowerCase())) {
								resultat.add(produitsName.get(i));
								break;
							}
			}
			System.out.println();
		}

*/


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < produitsNameP.get(i).size(); j++) {
                System.out.println("produitsName " + produitsNameP.get(i).get(j));
            }
        }


        ArrayList<ArrayList<String>> ingredientsP = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < m; i++) {
            String[] ingredientsSplit = ingredients.get(i).split(" ");
            ArrayList<String> ingredientToAdd = new ArrayList<String>();
            for (String nameIngredient : ingredientsSplit) {
                if (nameIngredient.length() >= 3 && !Character.isDigit(nameIngredient.charAt(0)) && !nameIngredient.toLowerCase().equals("des") && !nameIngredient.toLowerCase().equals("les") && !nameIngredient.toLowerCase().equals("aux") && !nameIngredient.toLowerCase().equals("une")) {
                    if (nameIngredient.toLowerCase().substring(0, 1) == "l'" || nameIngredient.toLowerCase().substring(0, 1) == "d'" || nameIngredient.toLowerCase().substring(0, 1) == "s'")
                        ingredientToAdd.add(nameIngredient.toLowerCase().substring(2, nameIngredient.length() - 1));
                    if(nameIngredient.charAt(0) == '('){
                        ingredientToAdd.add(nameIngredient.toLowerCase().substring(1, nameIngredient.length() - 1));
                        break;
                    }
                    if(nameIngredient.charAt(nameIngredient.length() -1) == ')') {
                        ingredientToAdd.add(nameIngredient.toLowerCase().substring(0, nameIngredient.length() -2));
                        break;
                    }
                    ingredientToAdd.add(nameIngredient.toLowerCase());

                }
            }
            ingredientsP.add(ingredientToAdd);
        }
        m = ingredientsP.size();
        System.out.println();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < ingredientsP.get(i).size(); j++) {
                System.out.println("ingredients " + ingredientsP.get(i).get(j));
            }
        }

        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                boolean doBreak = false;
                int ingredientsStringSize = ingredientsP.get(j).size();
                int productStringSize = produitsNameP.get(i).size();

                for (int k = 0; k < ingredientsStringSize; k++) {
                    for (int l = 0; l < productStringSize; l++) {
                        System.out.println(ingredientsP.get(j).get(k));
                        System.out.println(produitsNameP.get(i).get(l));
                        if (isEqual(ingredientsP.get(j).get(k), produitsNameP.get(i).get(l))
                        ) {
                            System.err.println("heyy");
                            resultat.add(products.get(i));
                            doBreak = true;
                            break;
                        }
                    }
                    if (doBreak)
                        break;
                }

            }
            System.out.println();
            System.out.println();
            System.out.println();

        }


       /* for (Product res : resultat)
            System.out.println(res);
        System.out.println();
        System.out.println();*/

        String test = "My first arg test";
        Boolean flag = Character.isDigit(test.charAt(0));

        String[] words = test.split(" ");

        for (String word : words) System.out.println(word);
        // On teste si la chaine de caractere a une longuer superieru ou égale à 3
        // On teste si le premier caractere du mot est un nombre

        return resultat;
    }


    private  boolean isEqual(String firstString,String secondString) {

        firstString = firstString.toLowerCase();
        secondString = secondString.toLowerCase();

        String firstStringWithS = firstString + "s";
        String secondStringWithS = secondString + "s";

        boolean AreStringEqual = false;
        if(firstString.equals(secondString))
            AreStringEqual =  true;
        else if( firstStringWithS.equals(secondString))
            AreStringEqual =  true;
        else if( firstString.equals(secondStringWithS))
            AreStringEqual =  true;
        else if( firstStringWithS.equals(secondStringWithS))
            AreStringEqual =  true;

        return AreStringEqual;
    }

    public ArrayList<Product> getImportProduct(int number) {
        ArrayList<Product> resultat = new ArrayList<Product>();

        String ingredient = ingredients.get(number);
		/*
		for(int i=0; i<n ; i++) {
			for(int j=0; j<m; j++) {
					System.out.println(ingredients.get(j).toLowerCase());
					System.out.println(produitsName.get(i).toLowerCase());
					int ingredientSize = ingredients.get(j).length();
					int prouctSize = produitsName.get(i).length();

						if(ingredientSize > prouctSize)
							if(ingredients.get(j).toLowerCase().contains(produitsName.get(i).toLowerCase())) {
								resultat.add(produitsName.get(i));
								break;
							}
						else
							if(produitsName.get(i).toLowerCase().contains(ingredients.get(j).toLowerCase())) {
								resultat.add(produitsName.get(i));
								break;
							}

			}
			System.out.println();
		}

*/


        ArrayList<String> ingredientsP = new ArrayList<String> ();
        String[] ingredientsSplit = ingredient.split(" ");
        for (String nameIngredient : ingredientsSplit) {
            if (nameIngredient.length() >= 3 && !Character.isDigit(nameIngredient.charAt(0)) && !nameIngredient.toLowerCase().equals("des") && !nameIngredient.toLowerCase().equals("les") && !nameIngredient.toLowerCase().equals("aux") && !nameIngredient.toLowerCase().equals("une"))
                ingredientsP.add(nameIngredient.toLowerCase());
        }

    System.out.println();
        for (int j = 0; j < ingredientsP.size(); j++) {
            System.out.println("ingredients " + ingredientsP.get(j));
        }
        Log.e("ZDIUDUSJIDFJIFDSJSDFJSFDKFKSODFFSKKLDLKSDLDSLSDLFDLSD", ""+products.size() + "    " + ""+produitsNameP.size());

        System.out.println();
        for (int i = 0; i < n; i++) {
                boolean doBreak = false;
                int ingredientsStringSize = ingredientsP.size();
                int productStringSize = produitsNameP.get(i).size();

                for (int k = 0; k < ingredientsStringSize; k++) {
                    for (int l = 0; l < productStringSize; l++) {

                        if (isEqual(ingredientsP.get(k), produitsNameP.get(i).get(l))
                        ) {
                            resultat.add(products.get(i));
                            doBreak = true;
                            break;
                        }
                    }
                    if (doBreak)
                        break;
                }

            }

        String test = "My first arg test";
        Boolean flag = Character.isDigit(test.charAt(0));

        String[] words = test.split(" ");

        for (String word : words) System.out.println(word);
        // On teste si la chaine de caractere a une longuer superieru ou égale à 3
        // On teste si le premier caractere du mot est un nombre

        return resultat;
    }



}
